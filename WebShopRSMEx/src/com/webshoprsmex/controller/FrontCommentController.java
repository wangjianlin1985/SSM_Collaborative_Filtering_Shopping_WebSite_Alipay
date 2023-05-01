package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.Comment;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.CommentService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 前台评论记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/comment")
public class FrontCommentController extends BaseController{

	@Autowired
	private CommentService commentService;//注入评论记录业务类
	
	/**
	 * 分页带参数查询，我的评论记录
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize){
		//查询参数集合
		User cUser = getCurrentUser();
		PageHelper.startPage(pageNum, pageSize);
		params.add(new Object[]{"c.userid","=",cUser.getId()});
		List<Comment> list = commentService.findJoin(params);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
		map.put("pageBean", pageInfo);
		return "front/comment/list";
	}
	
	/**
	 * 跳转到评论详情页面
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Integer commentid){
		//查询参数集合
		User cUser = getCurrentUser();
		params.add(new Object[]{"c.userid","=",cUser.getId()});
		params.add(new Object[]{"c.id","=",commentid});
		Comment comment = commentService.findFirstJoin(params);
		model.addAttribute("comment", comment);
		return "front/comment/view";
	}
	
	/**
	 * 跳转到评论编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Integer commentid){
		//查询参数集合
		User cUser = getCurrentUser();
		params.add(new Object[]{"c.userid","=",cUser.getId()});
		params.add(new Object[]{"c.id","=",commentid});
		Comment comment = commentService.findFirstJoin(params);
		model.addAttribute("comment", comment);
		return "front/comment/edit";
	}
	
	/**
	 * 添加/修改评论，ajax异步请求，返回json格式数据
	 * @param comment 评论对象
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Object saveOrUpdate(Comment comment){
		int success = 0;
		if(comment.getId()!=null){//修改
			Comment oldComment = commentService.selectByPrimaryKey(comment.getId());
			if(oldComment!=null){
				oldComment.setContent(comment.getContent());
				success = commentService.updateByPrimaryKeySelective(oldComment);
			}
		}else{//保存
			comment.setUserid(getCurrentUser().getId());;
			comment.setCreatetime(DateUtil.getCurrentDate());
			success = commentService.insertSelective(comment);
		}
		resultMap.put("url","reload");
		resultMap.put("success",success);
		return resultMap;
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