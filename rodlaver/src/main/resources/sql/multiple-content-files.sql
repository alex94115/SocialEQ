DROP PROCEDURE IF EXISTS multiple_content_files;
DELIMITER $$
CREATE PROCEDURE multiple_content_files()
READS SQL DATA
BEGIN

	DECLARE no_more_rows BOOLEAN;
	DECLARE loop_cntr INT DEFAULT 0;
	DECLARE num_rows INT DEFAULT 0;
	
	DECLARE id bigint(20) DEFAULT 0;
	DECLARE contentFilename varchar(255) DEFAULT NULL;
	DECLARE digitalContentOriginalFilename varchar(512) DEFAULT NULL;
	DECLARE digitalContentType varchar(255) DEFAULT NULL;
	
	DECLARE digital_content_listing_cursor CURSOR FOR 
		SELECT id, contentFilename
		FROM listing 
		WHERE contentFilename IS NOT NULL;
		
	 -- Declare 'handlers' for exceptions
  	DECLARE CONTINUE HANDLER FOR NOT FOUND
    	SET no_more_rows = TRUE;
	
	OPEN digital_content_listing_cursor;
	select FOUND_ROWS() into num_rows;
	
	the_loop: LOOP
	
		FETCH digital_content_listing_cursor 
		INTO 	id
		, 		contentFilename;
		
		IF no_more_rows THEN
	        CLOSE digital_content_listing_cursor;
	        LEAVE the_loop;
	    END IF;
	    
	    SELECT id;
	
	    -- count the number of times looped
    	SET loop_cntr = loop_cntr + 1;

  	END LOOP the_loop;

  -- 'print' the output so we can see they are the same
  select num_rows, loop_cntr;

END $$
DELIMITER ;