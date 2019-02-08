CREATE TABLE `digipay_super_admin`.`project_task_worklog` (
  `n_worklog_id` BIGINT NOT NULL,
  `n_seconds_worked` INT NOT NULL,
  `n_td_user_id` INT NOT NULL,
  `vch_user_name` VARCHAR(200) NOT NULL,
  `n_task_id` BIGINT NOT NULL,
  `vch_task_name` VARCHAR(255) NOT NULL,
  `vch_task_url` VARCHAR(255) NULL,
  `n_project_id` BIGINT NOT NULL,
  `vch_project_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`n_worklog_id`));

CREATE TABLE `digipay_super_admin`.`project_task` (
  `n_task_id` BIGINT NOT NULL,
  `n_user_id` BIGINT NOT NULL,
  `n_project_id` BIGINT NULL,
  `vch_task_name` VARCHAR(255) NULL,
  `b_active` TINYINT(1) NULL,
  `n_assigned_by_id` BIGINT NULL,
  `vch_td_url` VARCHAR(255) NULL,
  `vch_project_system_url` VARCHAR(255) NULL,
  PRIMARY KEY (`n_task_id`));
