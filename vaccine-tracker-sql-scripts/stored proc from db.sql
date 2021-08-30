CREATE DEFINER=`admin`@`%` PROCEDURE `addNewSubscription`(IN emailIn varchar(30),IN districtId integer, IN vaccineType varchar(25),OUT insertType INT)
BEGIN  
	Declare jobId INT default -1;
      
	Select job_id into jobId from JOB_DETAILS where district_id=districtId and vaccine_type=vaccineType;
if jobId>0 then
    if exists(select SUBSCRIPTION_ID from SUBSCRIPTION where EMAIL=emailIn and JOB_ID =jobId and ISACTIVE=0) then
		update SUBSCRIPTION set ISACTIVE=1 where EMAIL=emailIn and JOB_ID=jobID;
        set insertType=1;
	elseif (select COUNT(SUBSCRIPTION_ID) from SUBSCRIPTION where EMAIL=emailIn and JOB_ID =jobId and ISACTIVE=1)>0 then 
            set insertType=2;
	else
		INSERT INTO SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) values(emailIn,jobId,true);
        set insertType=3;
	end if;
else
	set insertType=-1;
end if;
END