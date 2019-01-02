variable "aws_access_key" {
  description = "AWS access key"
}

variable "aws_secret_key" {
  description = "AWS secret access key"
}

variable "aws_region" {
  description = "AWS region"
}

variable "ecs_key_name" {
  description = "EC2 instance key pair name"
}

variable "ecs_cluster" {
  description = "ECS cluster name"
}

variable "availability_zone" {
  description = "availability zone used for the demo, based on region"
  default = {
    us-east-2 = "us-east-2"
  }
}

variable "aws_elb_name" {
	description = "name for the enterprise load balancer"
}

########################### Autoscale Config ################################

variable "max_instance_size" {
  description = "Maximum number of instances in the cluster"
}

variable "min_instance_size" {
  description = "Minimum number of instances in the cluster"
}

variable "desired_capacity" {
  description = "Desired number of instances in the cluster"
}

############################# Security Group Vars #################################
variable "security_group_name" {
  description = "name to use for the security group"
}

variable "elb_security_group_name" {
  description = "name to use for the security group"
}

variable "security_group_cidr_block" {
	description = "the value for the security group cidr block"
}