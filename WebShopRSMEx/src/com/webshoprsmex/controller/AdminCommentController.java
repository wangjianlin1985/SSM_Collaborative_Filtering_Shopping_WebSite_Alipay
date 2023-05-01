package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.Comment;
import com.webshoprsmex.service.CommentService;
import com.webshoprsmex.util.Constant;

/**
 * 后台管理员评论控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/comment")
public class AdminCommentController extends BaseController{

	@Autowired
	private CommentService commentService;//注入评论业务类
	
	/**
	 * 带参数分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			String username,String itemname){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"u.username","like",username});
		params.add(new Object[]{"i.itemname","like",itemname});
		List<Comment> list = commentService.findJoin(params);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
		map.put("pageBean", pageInfo);
		map.put("username", username);
		map.put("itemname", itemname);
		return "admin/comment/list";
	}
	
	/**
	 * 跳转到评论详情页面
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Integer commentid){
		params.add(new Object[]{"c.id","=",commentid});
		Comment comment = commentService.findFirstJoin(params);
		request.setAttribute("comment", comment);
		return "admin/comment/view";
	}
	
	/**
	 * 删除
	 * @param comment
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Comment comment){
		resultMap.put("success",commentService.deleteByPrimaryKey(comment.getId()));//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
}