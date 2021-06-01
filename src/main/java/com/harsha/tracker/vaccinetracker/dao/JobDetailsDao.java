package com.harsha.tracker.vaccinetracker.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.harsha.tracker.vaccinetracker.model.JobDetails;

@Repository
public class JobDetailsDao {
	
	private static final BeanPropertyRowMapper<JobDetails> ROW_MAPPER = new BeanPropertyRowMapper<JobDetails>(JobDetails.class);
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public JobDetailsDao(DataSource ds) {
		this.jdbcTemplate= new JdbcTemplate(ds);
	}
	public List<JobDetails> findAllJobDetails() {
		return jdbcTemplate.query("SELECT * FROM JOB_DETAILS WHERE ISACTIVE=?",new Object[] {true},
				new int[] {java.sql.Types.BOOLEAN},ROW_MAPPER);
	}
	public List<JobDetails> findJobdetailsById(int jobId) {
		return jdbcTemplate.query("select * from JOB_DETAILS where JOB_ID=? and ISACTIVE=?",new Object[] {jobId,true},
				new int[] {java.sql.Types.INTEGER,java.sql.Types.BOOLEAN},ROW_MAPPER);
	}
	public int addNewJob(JobDetails input) {
		return jdbcTemplate.update("insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (?,?,?,?)", 
				new Object[] {input.getSTATE_ID(),input.getDISTRICT_ID(),input.getVACCINE_TYPE(),input.isISACTIVE()});
	}
	public int deleteJobById(int jobId) {
		return jdbcTemplate.update("update JOB_DETAILS set isactive=false where job_id=?", 
				new Object[] {jobId});
	}
	public void deleteAllJobs() {
		 jdbcTemplate.update("update JOB_DETAILS set ISACTIVE=FALSE");
	}
}
