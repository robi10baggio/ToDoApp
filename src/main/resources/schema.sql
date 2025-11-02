DROP TABLE IF EXISTS teams CASCADE ;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS todo_items CASCADE ;

CREATE TABLE teams(
	id 			SERIAL PRIMARY KEY,
	team_name 	varchar(40) NOT NULL
);

CREATE TABLE users(
	id 			SERIAL PRIMARY KEY,
	user_id 	varchar(40)  UNIQUE NOT NULL,
	user_name 	varchar(40)  NOT NULL,
	password 	varchar(40)  NOT NULL,
	team_id int  REFERENCES teams(id)
);


CREATE TABLE todo_items(
	id 				SERIAL PRIMARY KEY,
	task_content 	varchar(200) NOT NULL,
	status	 		int default 0,
	time_limit 		date  NOT NULL,
	user_id int 	REFERENCES users(id),
	team_id int  	REFERENCES teams(id)
);