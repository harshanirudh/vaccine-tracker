package com.harsha.tracker.vaccinetracker.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.harsha.tracker.vaccinetracker.model.Subscription;
@Repository
public class SubscriptionDao {

	private JdbcTemplate jdbcTemplate;
	private static final BeanPropertyRowMapper<Subscription> ROW_MAPPER = new BeanPropertyRowMapper<Subscription>(Subscription.class);
	@Autowired
	public SubscriptionDao(DataSource ds) {
		this.jdbcTemplate= new JdbcTemplate(ds);
	}
	public List<Subscription> findAllSubscriptions() {
		return jdbcTemplate.query("select * from SUBSCRIPTION where ISACTIVE=TRUE",ROW_MAPPER );
	}
	public List<Subscription> findSubscriptionById(int subId) {
		return jdbcTemplate.query("select * from SUBSCRIPTION where SUBSCRIPTION_ID=?",
				new Object[] {subId},new int[] {java.sql.Types.INTEGER},ROW_MAPPER );
	}
	public List<Subscription> findSubscriptionByJobId(int jobId) {
		return jdbcTemplate.query("select * from SUBSCRIPTION where JOB_ID=? and ISACTIVE=true",
				new Object[] {jobId},new int[] {java.sql.Types.INTEGER},ROW_MAPPER );
	}
	/** 
	 * To Add an email which subscribes to a particular job<br>
	 * @param email Email of the user for subscription
	 * @param districtId District id prefered by the user
	 * @param vaccine vaccine type for subscription 
	 * @return
	 * Stored proc returns out param insertType integer<br>
	 * <strong> 1 </strong> for if the email is present which was soft deleted and it has been updated to be active for subscription again<br>
	 * <strong> 2 </strong> when the email is subscribed already<br>
	 * <strong> 3 </strong> for the email has been inserted successfully for subscription<br>
	 * <strong>-1</strong> if the job id is not present, no subscription has been done
	 * @return 
	 * **/
	public int addNewSubscription(String email,int districtId,String vaccine) {
		SimpleJdbcCall procCall= new SimpleJdbcCall(jdbcTemplate);
		procCall.setProcedureName("addNewSubscription");
		MapSqlParameterSource paramMap=new MapSqlParameterSource();
		paramMap.addValue("emailIn", email);
		paramMap.addValue("districtId", districtId);
		paramMap.addValue("vaccineType", vaccine);
		Map<String, Object> result = procCall.execute(paramMap);
		return (int) result.get("insertType");
	}
	public int deleteSubscription(String email,int districtId,String vaccine) {
		String sql="update SUBSCRIPTION set ISACTIVE=false \r\n" + 
				"where job_id in ( select job_id from JOB_DETAILS where district_id=? and vaccine_type=?) and email=?";
		return jdbcTemplate.update(sql,new Object[] {districtId,vaccine,email},
				new int[] {java.sql.Types.INTEGER,java.sql.Types.VARCHAR,java.sql.Types.VARCHAR,});
		
	}
}
