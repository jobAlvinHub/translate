package cn.zvo.translate.tcdn.admin.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import cn.zvo.translate.tcdn.admin.vo.TranslateSiteListVO;
import cn.zvo.translate.tcdn.admin.vo.TranslateSiteVO;
import cn.zvo.translate.tcdn.core.entity.TranslateSite;
import cn.zvo.translate.tcdn.core.entity.TranslateSiteSet;

/**
 * 翻译的站点
 * @author <a href="https://github.com/xnx3/writecode">WriteCode自动生成</a>
 */
@Controller(value = "TranslateSiteController")
@RequestMapping("/translate/mirrorimage/translateSite/")
public class TranslateSiteController extends BaseController {

	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 列表信息
	 * @param token 当前操作用户的登录标识
	 * 			<p>可通过 <a href="xxxxx.html">/xxxx/login.json</a> 取得</p>
	 * TODO [tag-3] 
	 * @param id 搜索项，主键,自动编号 
	 * @param name 搜索项，站点名字，只是给自己看的，方便辨别 
	 * @param language 搜索项，当前语种，当前站点的语种是什么，如 english ，跟 http://api.translate.zvo.cn/doc/language.json.html 这里的值对应 
	 * @param url 搜索项，站点网址，格式如 http://www.zvo.cn 注意一定要带上前面的协议 
	 * @param everyNumber 每页显示多少条数据。取值 1～100，
	 * 			<p>最大显示100条数据，若传入超过100，则只会返回100条<p>
	 * 			<p>若不传，默认显示15条</p>
	 * @param currentPage 获取第几页的数据。若不传，默认请求第一页的数据
	 * @return 若成功，则返回列表数据
	 * @author <a href="https://github.com/xnx3/writecode">WriteCode自动生成</a>
	 */
	@ResponseBody
	@RequestMapping(value = "list.json", method = {RequestMethod.POST})
	public TranslateSiteListVO list(
			@RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber,
			@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
			HttpServletRequest request) {

        /**
         * TODO 列表功能调整
         * 实体类 TranslateSite
         * 说明：
         * 	1.根据实际需求，设置WHERE条件
         *      * 使用 sql.appendWhere() 方法，设置各种WHERE条件
         *      * 例如：sql.appendWhere("id = 1");
         *      * 详见 tag-1
         * 	2.列表可以通过某些字段进行筛选
         * 		* 调整 sql.setSearchColumn() 方法的内容，将其中的字段进行删除或调整
         * 		* 调整参数注释中的内容
         *      * 详见 tag-2，tag-3
         * 	3.列表根据实际需求设置排序
         * 		* 使用 sql.setDefaultOrderBy() 方法，设置 sort 正序排序即可
         * 		* 详见 tag-4
         * 	4.根据实际需求，调整jsp页面内容
         * 		* 例如：删除不需要显示的字段；删除不需要筛选的字段。
         * 		* 页面位置：src/main/webapp/translate/mirrorimage/translateSite/list.jsp
         * 		* 详见页面中 tag-5，tag-6，tag-7
         * 	技术文档参考：
         * 		https://e.gitee.com/leimingyun/doc/share/8ca49d527b5f4403/?sub_id=3601239
         * 		https://gitee.com/leimingyun/wm/blob/master/src/main/java/com/xnx3/j2ee/util/Sql.java
         * 		https://gitee.com/leimingyun/wm/blob/master/src/main/java/com/xnx3/j2ee/util/Page.java
         */

		TranslateSiteListVO vo = new TranslateSiteListVO();

		// 创建Sql
		Sql sql = new Sql(request);
		// 配置查询那个表
		sql.setSearchTable("translate_site");
		// TODO [tag-1] 增加更多查询条件
		//sql.appendWhere("xxx = " + xxx);
		// TODO [tag-2] 查询条件-配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"id", "name", "language", "url", "xxxxxxx"});
		// 查询数据表的记录总条数
		int count = sqlService.count("translate_site", sql.getWhere());
		
		// 配置每页显示多少条，进行分页，取得分页信息
		Page page = new Page(count, everyNumber, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM translate_site ", page);
		// TODO [tag-4] 选择排序方式 当用户没有选择排序方式时，系统默认 主键 ASC 排序
		//sql.setDefaultOrderBy("id DESC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<TranslateSite> list = sqlService.findBySql(sql, TranslateSite.class);
		
		vo.setList(list);
		vo.setPage(page);
		
		// 日志记录
		ActionLogUtil.insert(request, "查看 翻译的站点 列表");
		
		return vo;
	}
	
	/**
	 * 获取某条的数据
	 * @param token 当前操作用户的登录标识 <required>
	 * 			<a href="xxxxx.html">/xxxx/login.json</a> 取得</p>
	 * @param id 主键
	 * @return 若成功，则可获取此条信息
	 * @author <a href="https://github.com/xnx3/writecode">WriteCode自动生成</a>
	 */
	@ResponseBody
	@RequestMapping(value = "details.json", method = {RequestMethod.POST})
	public TranslateSiteVO details(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		TranslateSiteVO vo = new TranslateSiteVO();

		if(id != 0) {
			TranslateSite entity = sqlService.findById(TranslateSite.class, id);
			if(entity == null){
				vo.setBaseVO(BaseVO.FAILURE, "要修改的信息不存在");
				return vo;
			}
			vo.setTranslateSite(entity);
			ActionLogUtil.insert(request, "获取 translate_site.id 为 " + id + " 的信息", entity.toString());
		}else {
			vo.setBaseVO(BaseVO.FAILURE, "请传入id");
			return vo;
		}
		
		return vo;
	}
	
	/**
	 * 添加或修改一条记录
	 * @param token 当前操作用户的登录标识 <required>
	 * 			<a href="xxxxx.html">/xxxx/login.json</a> 取得</p>
	 * TODO [tag-8] 
	 * @param id 主键,自动编号 
	 * @param name 站点名字，只是给自己看的，方便辨别 
	 * @param language 当前语种，当前站点的语种是什么，如 english ，跟 http://api.translate.zvo.cn/doc/language.json.html 这里的值对应 
	 * @param url 站点网址，格式如 http://www.zvo.cn 注意一定要带上前面的协议 
	 * @return 保存结果
	 * @author <a href="https://github.com/xnx3/writecode">WriteCode自动生成</a>
	 */
	@ResponseBody
	@RequestMapping(value = "save.json", method = {RequestMethod.POST})
	public BaseVO save(
	        // TODO [tag-9]
			//@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "language", required = false, defaultValue = "") String language,
			@RequestParam(value = "url", required = false, defaultValue = "") String url,
			HttpServletRequest request) {

        /**
         * TODO 添加编辑功能调整
         * 说明：
         * 	1.根据实际需求，调整要传递的参数，以及保存的数据
         * 		* 详见 tag-8，tag-9，tag-10
         * 	2.根据实际需求，在添加或编辑时，主动设置部分字段的值
         * 		* 例如：添加时设置创建时间 entity.setCreateTime(nowTime);
         *  3.根据实际需求，调整jsp页面内容
         *  	* 例如：删除不需要保存的字段；不能编辑的字段，将其input隐藏。
         *  	* 页面位置：src/main/webapp/translate/mirrorimage/translateSite/edit.jsp
         *  	* 详见页面中 tag-11
         */

		// 创建一个实体
		TranslateSite entity;
		if(id - 0 == 0) {
			// 添加
			entity = new TranslateSite();
			
			//查询是否已经存在，存在了就不能添加了
			TranslateSite site = sqlService.findAloneByProperty(TranslateSite.class, "url", url);
			if(site != null) {
				return error("源站["+url+"]已存在！请不要重复添加");
			}
			
		} else {
			// 修改
			entity = sqlService.findById(TranslateSite.class, id);
			if(entity == null) {
				return error("根据id，没查到该信息");
			}
		}
		
		// TODO [tag-10] 给实体赋值 
		entity.setName(name);
		entity.setLanguage(language);
		entity.setUrl(url);
		// 保存实体
		sqlService.save(entity);
		
		// 日志记录
		if(id - 0 == 0) {
			ConsoleUtil.log(entity.toString());
			
			TranslateSiteSet siteSet = new TranslateSiteSet();
			siteSet.setId(entity.getId());
			siteSet.setExecuteJs("");
			siteSet.setHtmlAppendJs("");
			ConsoleUtil.log(siteSet.toString());
			sqlService.save(siteSet);
			
			ActionLogUtil.insertUpdateDatabase(request, "向 translate_site 新增一条记录", entity.toString());
		}else{
			ActionLogUtil.insertUpdateDatabase(request, "修改 translate_site.id 为 " + id + " 的记录", entity.toString());
		}
		
		//刷新缓存
		sqlCacheService.deleteCacheById(TranslateSite.class, entity.getId());
		
		return success(entity.getId()+"");
	}
	
	/**
	 * 删除记录
	 * @param id 要删除的记录的id
	 * @param token 当前操作用户的登录标识 <required>
	 * 			<a href="xxxxx.html">/xxxx/login.json</a> 取得</p>
	 * @return 若成功，则可以删除成功
	 * @author <a href="https://github.com/xnx3/writecode">WriteCode自动生成</a>
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json", method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {

		/**
         * TODO 删除功能调整
         * 说明：
         * 	1.当前删除使用的是物理删除，会将这条数据从数据库中删除掉，实际开发中不建议这样做，所以这里需要调整删除功能的逻辑，
         * 		将其改为逻辑删除，也就是通过某个字段的值，来记录该数据是否已删除。
         * 		* 将物理删除 sqlService.delete() 改为逻辑删除
         * 		  例如：
         *          - 将
         *          -   sqlService.delete(); // 物理删除，会从表中删除此条数据
         *          - 改为
         *          -   entity.setIsDelete(delete); // 逻辑删除，通过设置某字段状态实现
         *          -   sqlService.save(entity); // 保存实体
         *      * 详见 tag-12
         */
		/*
		 *
		 * 1. 这里是物理
		 * 2. 要判断是否具有删除权利
		 *
		 */
			
		if(id - 0 <= 0) {
			return error("请传入id");
		}
		
		TranslateSite entity = sqlService.findById(TranslateSite.class, id);
		if(entity == null) {
			return error("要删除的记录不存在");
		}
		
		// TODO [tag-12] 现在是物理删除，改为逻辑删除
		sqlService.delete(entity);
		
		//刷新缓存
		sqlCacheService.deleteCacheById(TranslateSite.class, entity.getId());
		
		// 日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "删除 translate_site.id 为 " + id + " 的记录", entity.toString());
		
		return success();
	}
	
}