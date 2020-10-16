package com.xinou.lawfrim.sso.controller;



import com.xinou.lawfrim.common.util.APIResponse;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by zhangbo on 2017/10/23.
 * 系统用户管理
 */
public interface UserSSOController {

    /**
     * 添加系统用户
     *
     * @param account  账号
     * @param realName 真实姓名
     * @param mobile 电话号
     * @param roleIds  角色id数组
     * @param appIds  appId
     */
    APIResponse addUser(String account, String realName, String mobile, Integer[] roleIds, Integer[] appIds);


    /**
     * 删除用户
     *
     * @param id 用户id
     */
    APIResponse delUser(int id);


    /**
     * 修改系统用户
     *
     * @param id       id
     * @param realName 真实姓名
     * @param password 密码
     * @param mobile 电话号
     * @param roleIds  角色id数组
     * @param isEnable 是否可用  0:可用 1:不可用
     */
    APIResponse updateUser(int id, String realName, String password, String mobile, Integer[] roleIds, int isEnable);


    /**
     * 系统用户列表
     *
     * @param page        当前页数
     * @param pageSize    显示个数
     * @param selectValue 查询条件
     * @param searchValue 搜索值
     */
    APIResponse userList(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                         @RequestParam(required = false, defaultValue = "") String selectValue,
                         @RequestParam(required = false, defaultValue = "") String searchValue);

    /**
     * 根据id获取详情
     *
     * @param id id
     */
    APIResponse getUserById(int id);


    /**
     * 修改所有人密码
     * @param id 用户id
     * @param newPwd 新密码
     * @return
     */
    APIResponse updateAllPwd(int id, String newPwd);


    /**
     * @api {post} /admin/user/updatePwd 修改用户的密码
     * @apiDescription 修改用户的密码
     * @apiGroup  AdminLogin
     * @apiParam {String} oldPwd 旧密码
     * @apiParam {String} newPwd 新密码
     * @apiParamExample {string} 请求参数格式:
     *    {map}
     *
     * @apiVersion 1.0.0
     *
     * @apiErrorExample {json} 错误返回值:
     *     {
     *        "recode": 100,1009
     *         "remsg": "error,旧密码与当前密码不相符"
     *          "body": {
     *          }
     *     }
     *
     *
     * @apiSuccess {Number} recode 返回码
     * @apiSuccess {String} remsg 返回消息
     * @apiSuccess {map} body 返回数据体
     * @apiSuccessExample {json} 正确返回值:
     *     {
     *   "recode": 200,
     *   "remsg": "success",
     *   "body": {}
     *   }
     */
    APIResponse updatePwd(HttpSession session, String oldPwd, String newPwd);


    /**
     * @api {post} /admin/user/listByRole 通过角色获取用户列表
     * @apiDescription 通过角色获取用户列表
     * @apiGroup  AdminLogin
     * @apiParam {String} roleId 权限id(Number)
     * @apiParamExample {string} 请求参数格式:
     *    {map}
     *
     * @apiVersion 1.0.0
     *
     * @apiErrorExample {json} 错误返回值:
     *     {
     *        "recode": 100
     *         "remsg": "error"
     *          "body": {
     *          }
     *     }
     *
     *
     * @apiSuccess {Number} recode 返回码
     * @apiSuccess {String} remsg 返回消息
     * @apiSuccess {map} body 返回数据体
     * @apiSuccessExample {json} 正确返回值:
     *     {
     *   "recode": 200,
     *   "remsg": "success",
     *   "body": {
     *       "dataList":[{
     *           "id","id",
     *           "account","账户",
     *           "realName","真实姓名",
     *           "mobile","电话号",
     *       }]
     *   }
     *   }
     */
    APIResponse listByRole(Integer roleId);

}
