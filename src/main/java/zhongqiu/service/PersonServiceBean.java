package zhongqiu.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zhongqiu.bean.Person;

import java.util.List;

import javax.sql.DataSource;

//Spring事务注解
@Transactional
public class PersonServiceBean {
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 不受检查的异常，uncheckedException。事物会回滚操作
	public void delete_UncheckedException(Integer personid) {
		jdbcTemplate.update("delete from student where id=?", new Object[] { personid },
				new int[] { java.sql.Types.INTEGER });
		throw new RuntimeException("不受检查的异常");
	}

	// 受检查的异常，checkedException。事物不会回滚操作
	public void delete_CheckedException(Integer personid) throws Exception {
		jdbcTemplate.update("delete from student where id=?", new Object[] { personid },
				new int[] { java.sql.Types.INTEGER });
		throw new Exception("受检查的异常");
	}
	
	//注解方式实现异常回滚的级别
	@Transactional(rollbackFor=RuntimeException.class)   //不受检查的异常不回滚
	// @Transactional(noRollbackFor=Exception.class)     //受检查的异常不回滚
	// @Transactional(rollbackFor=Exception.class)       //受检查的异常回滚
	public void delete_AnnotationException(Integer personid) throws Exception {
		jdbcTemplate.update("delete from student where id=?", new Object[] { personid },
				new int[] { java.sql.Types.INTEGER });
		throw new Exception("受检查的异常");
	}

	//事务的传播属性，详细说明见学习笔记
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void delete(Integer personid) {
		jdbcTemplate.update("delete from student where id=?", new Object[] { personid },
				new int[] { java.sql.Types.INTEGER });
	}

	
	public Person getPerson(Integer personid) {
		return (Person) jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { personid },
				new int[] { java.sql.Types.INTEGER }, new PersonRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPersons() {
		return (List<Person>) jdbcTemplate.query("select * from student", new PersonRowMapper());
	}

	public void save(Person person) {
		jdbcTemplate.update("insert into student(name) values(?)", new Object[] { person.getName() },
				new int[] { java.sql.Types.VARCHAR });
	}

	public void update(Person person) {
		jdbcTemplate.update("update student set name=? where id=?", new Object[] { person.getName(), person.getId() },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.INTEGER });
	}
}
