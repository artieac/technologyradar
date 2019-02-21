variable "ecs_common_tag" {
	description = "A common tag for all items related to this project"
}

variable "aws_access_key" {
  description = "AWS access key"
}

variable "aws_secret_key" {
  description = "AWS secret access key"
}

variable "aws_region" {
  description = "AWS region"
  default = "us-east-2"
}

variable availability_zone {
  description = "availability zone used for the demo, based on region"
}
	
variable "availability_zones" {
  description = "availability zone used for the demo, based on region"
  default = ["us-east-2a", "us-east-2b", "us-east-2c"]
}


########################### VPC Config #####################################
variable "vpc_name" {
	description = "name for the vpc"
}

variable "vpc_cidr_block" {
	description = "cidr block for the vpc"
}

variable "igw_name" {
	description = "name for the internet gateway"
}

variable "public_subnet_name" {
	description = "name for the public subnet"
}

variable "public_subnet_count" {
	description = "number of public subnet cidrs"
}

variable "public_subnet_cidr" {
	description = "public subnet cidr block"
}

variable "route_table_name" {
	description = "name for the route table"
}

variable "route_table_cidr" {
	description = "route table cidr block"
}
	

############################# Security Group Config #################################
variable "public_security_group_name" {
  description = "name to use for the security group"
}

variable "public_security_group_cidr_01" {
	description = "the value for the security group cidr block"
}

variable "public_security_group_cidr_02" {
	description = "the value for the security group cidr block"
}

########################### Load Balancer Config #############################
variable "elb_name" {
	description = "name for the enterprise load balancer"
}

variable "elb_tg_name" {
	description = "name for the load balancer target group"
}

########################## Launch Configuration #############################
variable "launch_configuration_name" {
	description = "the name for the launch configuration"
}

variable "amis" {
  type = "map"
  default = {
    "us-east-1" = "ami-b374d5a5"
	"us-east-2" = "ami-731c2016"
    "us-west-2" = "ami-4b32be2b"
  }
}

variable "instance_type" {
	description = "the instance type to use for the launch configuraiton"
}

########################### Autoscale Config ################################

variable "autoscaling_group_name" {
	description = "name for the autoscaling group"
}

variable "max_instance_size" {
  description = "Maximum number of instances in the cluster"
}

variable "min_instance_size" {
  description = "Minimum number of instances in the cluster"
}

variable "desired_capacity" {
  description = "Desired number of instances in the cluster"
}

######################### ECS Config ######################################
variable "container_repository_name" {
	description = "The container repository name"
}

variable "ecs_service_role" {
	description = "the security role to use"
}

variable "ecs_key_name" {
  description = "EC2 instance key pair name"
}

variable "ecs_cluster" {
  description = "ECS cluster name"
}

variable "task_definition_family" {
	description = "ECS Task definition family name"
}

variable "service_definition_name" {
	description = "ECS Service name"
}


