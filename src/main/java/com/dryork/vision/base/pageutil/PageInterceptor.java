package com.dryork.vision.base.pageutil;

/**
 * 分页用拦截器
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {

	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		String sqlId = mappedStatement.getId();

		String originalSql = boundSql.getSql().trim();
		Object parameterObject = boundSql.getParameterObject();

		Page page = null;

		if (sqlId.matches(".+ByPage$")) { //只对以ByPage结尾的sql语句进行分页查询
			page = searchPageWithXpath(boundSql.getParameterObject(), ".", "page");//,"*/page"
		}
		if (page != null) {
			String countSql = getCountSql(originalSql);
			Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			PreparedStatement countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
			parameterHandler.setParameters(countStmt);
			ResultSet rs = countStmt.executeQuery();
			int totpage = 0;
			if (rs.next()) {
				totpage = rs.getInt(1);
			}
			rs.close();
			countStmt.close();
			connection.close();

			page.setTotalRecord(totpage);

			int offset = (page.getPageNo() - 1) * page.getPageSize();
			StringBuffer sb = new StringBuffer();
			sb.append(originalSql).append(" limit ").append(offset).append(",").append(page.getPageSize());
			BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());
			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
			/** 针对目前项目结构只处理list类型分页，后面想扩展再增加抽象工厂 no zuo no die */
			List<?> result = (List) invocation.proceed();
			page.setResultList(result);
			return result;
		}
		return invocation.proceed();

	}

	/**
	 * 根据给定的xpath查询Page对象
	 */
	private Page searchPageWithXpath(Object o, String... xpaths) {
		JXPathContext context = JXPathContext.newContext(o);
		Object result;
		for (String xpath : xpaths) {
			try {
				result = context.selectSingleNode(xpath);
			} catch (Exception e) {
				continue;
			}
			if (result instanceof Page) {
				return (Page) result;
			}
		}
		return null;
	}

	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		//    builder.keyProperty(ms.getKeyProperties()[0]);
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	/**
	 * 复制BoundSql对象
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
	}

	//	private static String prepareCountSql(String orgSql) {
	//		String fromSql = orgSql;
	//		fromSql = (new StringBuilder()).append("from ")
	//				.append(StringUtils.substringAfter(fromSql, "from")).toString();
	//		fromSql = StringUtils.substringBefore(fromSql, "order by");
	//		String countHql = (new StringBuilder()).append("select count(*) ")
	//				.append(fromSql).toString();
	//		return countHql;
	//	}

	public static void main(String[] args) {
		//		System.out.println(prepareCountSql("select * from a where ab =1 order by abc desc"));
		System.out.println("69527eb78bb862ddaf049b41a4708df7a46c6317fa0b3d9e2d62b59eae40406680bad25c343822af".length());
		System.out.println("65d5678631cef450395c1567c90ce0d061a1dfeae25ce5b919d49d478354ac099593fd9c8dc44515".length());
	}

	public class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {
	}
}
