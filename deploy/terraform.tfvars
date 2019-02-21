ecs_common_tag = "TechRadar"
aws_region = "us-east-2"

########################### VPC Vars ########################################
vpc_name = "TechRadar VPC"
vpc_cidr_block = "200.0.0.0/16"
igw_name = "TechRadar IGW"
public_subnet_name = "TechRadarPublicSubnet"
public_subnet_count = "2"
public_subnet_cidrs = ["200.0.0.0/24", "200.0.0.1/24"]
availability_zone = "us-east-2a"
public_subnet_cidr = "200.0.0.0/24"
route_table_name = "TechRadarRouteTable"
route_table_cidr = "0.0.0.0/0"

########################### Security Group Vars #############################
public_security_group_name="TF-TechRadar-Security-Group"
public_security_group_cidr_01="10.0.3.0/24"
public_security_group_cidr_02="10.0.4.0/24"

######################### Load Balancer Vars ###############################
elb_name = "TechRadar-ELB"
elb_tg_name = "TechRadar-ELB-TG"

######################### Launch Configuration Vars ###############################
launch_configuration_name = "ecs-launch-configuration"
target_ami="ami-b374d5a5"
instance_type="t2.micro"

######################## Auto scaling vars ################################
autoscaling_group_name="TechRadar_ASG"
desired_capacity = "1"
max_instance_size = "2"
min_instance_size = "1"

######################## ECS Vars ########################################
container_repository_name = "alwaysmoveforward/technologyradar"
ecs_service_role = "arn:aws:iam::215561421394:role/ecsServiceRole"
launch_configuration_name = "TechRadar_LaunchConfig"
ecs_key_name = "TechRadarTwo"
ecs_cluster = "TF-TechRadar"
task_definition_family = "TF-TechRadar-Task"
service_definition_name="TF-TechRadar-Service"
