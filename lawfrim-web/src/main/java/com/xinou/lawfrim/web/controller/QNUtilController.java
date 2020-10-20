package com.xinou.lawfrim.web.controller;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.util.upLoadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao_XX on date 2020/05/19.
 * Description:
 */
@RequestMapping("/qn/util/")
@RestController("QNUtilController")
public class QNUtilController {


    /**
     * 文件服务（七牛） 上传接口
     * @param file
     * @return
     */
    @PostMapping("/file/upload")
    public APIResponse upload(MultipartFile file){
        String name = upLoadFile.uploadFileQN(file);
        Map<String,Object> date = new HashMap<>();
        date.put("fileName",name);
        return new APIResponse(date);
    }

    /**
     * 文件服务（七牛）删除文件
     */
    @PostMapping("/file/del")
    public APIResponse delFile(String fileName){
        boolean res = upLoadFile.delFile(fileName,"");
        if (!res){
            return new APIResponse(Config.RE_CODE_DATABASE_ERROR,Config.RE_MSG_DATABASE_ERROR);
        }
        return new APIResponse();
    }


    /**
     * 文件服务（七牛） 上传接口(APP带标识)
     * @param file
     * @return
     */
    @PostMapping("/app/editor/upload")
    public APIResponse uploadIndex(MultipartFile file, String fileIndex){
        String name = upLoadFile.uploadFileQN(file);
        Map<String,Object> date = new HashMap<>();
        date.put("fileName",name);
        date.put("fileIndex",fileIndex);
        return new APIResponse(date);
    }
    /**
     * 下载图片的到本地
     * @param url
     * @param name
     * @return
     */
    public static boolean download(String url,String name){
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        FileOutputStream out = null;
        boolean flag = false;
        try
        {
            File file0=new File("/root/fileTest");
            if(!file0.isDirectory()&&!file0.exists()){
                file0.mkdirs();
            }
            out = new FileOutputStream(file0+"/"+name);
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            bis = new BufferedInputStream(inputStream);
            byte b [] = new byte[1024];
            int len = 0;
            while((len=bis.read(b))!=-1){
                out.write(b, 0, len);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(bis!=null){
                    bis.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
                return flag;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return flag;

    }


}
