USE VACCINE_TRACKER;
insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (32,581,'COVAXIN',TRUE);
insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (32,581,'COVISHIELD',TRUE);
insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (32,603,'COVAXIN',TRUE);
insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (32,603,'COVISHIELD',TRUE);
delete from JOB_DETAILS where job_id=6;
insert INTO JOB_DETAILS(STATE_ID,DISTRICT_ID,VACCINE_TYPE,ISACTIVE) values (32,000,'COVISHIELD',TRUE);Select last_insert_id();
SELECT * FROM JOB_DETAILS;

Update JOB_DETAILS set ISACTIVE=false;
select job_id from JOB_DETAILS where district_id=581 and vaccine_type='COVAXIN';
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) select 'harsha.anirudh@gmail.com',job_id,true from JOB_DETAILS where district_id=581 and vaccine_type='COVAXIN';
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) select 'harsha.anirudh@gmail.com',job_id,true from JOB_DETAILS where district_id=581 and vaccine_type='COVISHIELD';
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) select 'harsha.anirudh@gmail.com',job_id,true from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN';
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) select 'harsha.anirudh@gmail.com',job_id,true from JOB_DETAILS where district_id=603 and vaccine_type='COVISHIELD';
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) select 'harsha.anirudh97@gmail.com',job_id,true from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN';
select * from SUBSCRIPTION;
truncate table SUBSCRIPTION;
commit;
update SUBSCRIPTION set ISACTIVE=false 
where job_id = ( select job_id from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN') and email="harsha.anirudh97@gmail.com";
#######
#To check if a person is already subscribed
select count(SUBSCRIPTION_ID) from SUBSCRIPTION where EMAIL='harsha.anirudh98@gmail.com' and JOB_ID in( select job_id from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN');
## To check and add only if person is not subscribed before
INSERT INTO VACCINE_TRACKER.SUBSCRIPTION (EMAIL,JOB_ID,ISACTIVE) 
select 'harsha.anirudh97@gmail.com',job_id,true from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN'
and (select count(SUBSCRIPTION_ID) from SUBSCRIPTION where EMAIL='harsha.anirudh97@gmail.com' and JOB_ID in( select job_id from JOB_DETAILS where district_id=603 and vaccine_type='COVAXIN'))<=0;


create procedure