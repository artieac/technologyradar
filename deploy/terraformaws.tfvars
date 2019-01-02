ecs_key_name = "TechnologyRadarTwo"
ecs_cluster = "TF-Technology-Radar"
aws_region = "us-east-2"
desired_capacity = "1"
max_instance_size = "2"
min_instance_size = "1"
test_network_cidr = ""

########################### Security Group Vars #############################
security_group_name="TF-Technology-Radar-Security-Group"
security_group_cidr_block="172.30.6.0/24"

######################### Load Balancer Vars ###############################
aws_elb_name = "TF-Technology-Radar-ELB"
elb_security_group_name = "TF-ELB-Security-Group"
