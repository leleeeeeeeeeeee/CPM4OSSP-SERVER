package mpms.test.controller;

import cn.jiangzeyin.common.JsonMessage;
import mpms.test.dao.UserMapper;
import mpms.test.pojo.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class MyUserController {

	@Autowired
	UserMapper userMapper;

	// 查询全部用戶信息
	@GetMapping("/getUserList")
	public String getUsers(){
		return JsonMessage.getString(200, "", userMapper.queryAllUser());
	}

	// 查询用戶
	@GetMapping("/getUer/{id}")
	public MyUser getUser(@PathVariable("id") Integer id){
		return userMapper.queryUserById(id);
	}

}
