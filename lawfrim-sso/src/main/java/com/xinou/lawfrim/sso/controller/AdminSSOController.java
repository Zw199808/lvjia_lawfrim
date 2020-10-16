package com.xinou.lawfrim.sso.controller;




import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.AdminEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangbo on 2017/10/16.
 * 系统登录登出操作
 */
public interface AdminSSOController {

    /**
     * @api {post} /admin/login 管理系统登录接口
     * @apiDescription 管理系统登录接口
     * @apiGroup  AdminLogin
     * @apiParam {String} account 账号
     * @apiParam {String} pwd 密码
     * @apiParam {String} ip ipv4
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
     * @apiSuccess {String[]} body.permissions 权限数组
     * @apiSuccess {Object[]} body.roles 角色数组
     * @apiSuccess {String} body.roles.name 角色名称
     * @apiSuccess {Number} body.userId 管理系统用户id
     * @apiSuccessExample {json} 正确返回值:
     *     {
     *   "recode": 200,
     *   "remsg": "success",
     *   "body": {
     *       "permissions": ["/admin/news/info","/admin/news/sort"]
     *       "roles":[{
     *           "name":"编辑"
     *       }]
     *       "userId": 3
     *      }
     *   }
     */
    APIResponse adminLogin(HttpServletResponse response, AdminEntity adminEntity);


    /**
     * @api {post} /admin/logout 管理系统登出接口
     * @apiDescription 管理系统登出接口
     * @apiGroup  AdminLogin
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
     * @apiSuccessExample {json} 正确返回值:
     *     {
     *   "recode": 200,
     *   "remsg": "success",
     *   "body": {}
     *   }
     */
    APIResponse logout();

}
