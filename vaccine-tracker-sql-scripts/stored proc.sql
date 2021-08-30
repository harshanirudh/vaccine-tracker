# 1. To check if subscription is a soft delete then reuse the soft delete by updating isactive to true and return succesfully
# 2. If there is no field for soft delete then check if the user is subscribed already
# 3. if user is subscribed already donot insert
# 4. if user is not subscribed then insert

DELIMITER &&  
CREATE PROCEDURE addNewSubscription (IN email varchar(30),IN districtId integer, IN vaccineType varchar(25),OUT insertType INT)  
BEGIN  
	Declare jobId INT default -1;
      
	Select job_id into jobId from JOB_DETAILS where district_id=districtId and vaccine_type=vaccineType;
if jobId>0 then
    if exists(select SUBSCRIPTION_ID from SUBSCRIPTION where EMAIL=email and JOB_ID =jobId and ISACTIVE=0) then
		update SUBSCRIPTION set ISACTIVE=1 where EMAIL=email and JOB_ID=jobID;
        set insertType=1;
	elseif exists(select SUBSCRIPTION_ID from SUBSCRIPTION where EMAIL=email and JOB_ID =jobId and ISACTIVE=1) then 
			set insertType=2;
	else
		INSERT INTO SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) values(email,jobId,true);
        set insertType=3;
	end if;
else
	set insertType=-1;
end if;
END &&  
DELIMITER ;  
	