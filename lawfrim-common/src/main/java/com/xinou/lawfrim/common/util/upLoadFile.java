package com.xinou.lawfrim.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Created by xiao_XX on 2017/10/13.
 */
public class upLoadFile {

    //上传文件到七牛（流形式）
    public static String uploadFileQN(MultipartFile file){

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Config.BUCKET_ZONE_QN);
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = getFileNameNoEx(file.getOriginalFilename());

        //上传完成后返回的文件名
        String FileName = "";
        try {
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(Config.ACCESSKEY_QN, Config.SECRETKEY_QN);
            String upToken = auth.uploadToken(Config.BUCKET_NAME_QN);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println("上传完毕！");
                FileName = putRet.key;
//                System.out.println("上传完毕！文件名："+FileName);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }


        return FileName;
    }

    //上传文件到七牛（文件形式）
    public static boolean uploadFileQNUrl(String url)  {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Config.BUCKET_ZONE_QN);
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = Config.ACCESSKEY_QN;
        String secretKey = Config.SECRETKEY_QN;
        String bucket = Config.BUCKET_NAME_QN;
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/xiao_XX/Downloads/xinou-upload/"+url;
        String key = url;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (QiniuException ex) {
//            System.out.println(url);
////            Response r = ex.response;
////            System.err.println(r.toString());
            System.out.println("url==="+url);
        }
        return true;
    }

    /*
     * Java文件操作 拼接文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot)+"_"+System.currentTimeMillis()+filename.substring(dot);
            }
        }
        return filename;
    }


    public static boolean delFile(String key,String newKey){

        if (newKey.equals(key)){
            return true;
        }

        int successCode  = 200;

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Config.BUCKET_ZONE_QN);
//...其他参数参考类注释
        String accessKey = Config.ACCESSKEY_QN;
        String secretKey = Config.SECRETKEY_QN;
        String bucket = Config.BUCKET_NAME_QN;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response response = bucketManager.delete(bucket, key);
            if (response.statusCode == successCode){
                return true;
            }else {
                return false;
            }
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
    }



    public static <T> List<T> compare(T[] t1, T[] t2) {
        List<T> list1 = Arrays.asList(t2);
        List<T> list2 = new ArrayList<T>();
        for (T t : t1) {
            if (!list1.contains(t)) {
                list2.add(t);
            }
        }
        return list2;
    }


    public static boolean delFileBatch(String[] keyList){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        String accessKey = Config.ACCESSKEY_QN;
        String secretKey = Config.SECRETKEY_QN;
        String bucket = Config.BUCKET_NAME_QN;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //单次批量请求的文件数量不得超过1000
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {

                } else {
                    System.out.println(status.data.error);
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
            return false;
        }
        return true;

    }

    public static String ThImage(String icon){
        return Config.SERVICE_IMG_URL+ encodeURIComponent(icon)+Config.SERVICE_TH_IMG_URL;
    }

    public static String resourcesCode(String url){
        return Config.SERVICE_IMG_URL+encodeURIComponent(url);
    }

    public static String encode(String str) {
        String isoStr = null;
        try {
            isoStr = new String(str.getBytes("UTF8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char[] chars = isoStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] <= 'z' && chars[i] >= 'a')
                    || (chars[i] <= 'Z' && chars[i] >= 'A') || chars[i] == '-'
                    || chars[i] == '_' || chars[i] == '.' || chars[i] == '!'
                    || chars[i] == '~' || chars[i] == '*' || chars[i] == '\''
                    || chars[i] == '(' || chars[i] == ')' || chars[i] == ';'
                    || chars[i] == '/' || chars[i] == '?' || chars[i] == ':'
                    || chars[i] == '@' || chars[i] == '&' || chars[i] == '='
                    || chars[i] == '+' || chars[i] == '$' || chars[i] == ','
                    || chars[i] == '#') {
                sb.append(chars[i]);
            } else {
                sb.append("%");
                sb.append(Integer.toHexString(chars[i]));
            }
        }
        return sb.toString();
    }



    public static String decodeURIComponent(String s) {
        if (s == null) {
            return null;
        }

        String result = null;

        try {
            result = URLDecoder.decode(s, "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's <code>encodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The String to be encoded
     * @return the encoded String
     */
    public static String encodeURIComponent(String s) {
        String result = null;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }


    public static void main(String[] args) {

        String[] array1 = {"1"};
        String[] array2 = {};

        List<String> list = compare(array1,array2);
        for (String integer : list) {
            System.out.println(integer);
        }

    }

}
