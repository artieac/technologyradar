
provider "aws" {
  access_key = "${var.aws_access_key}"
  secret_key = "${var.aws_secret_key}"
  region = "${var.aws_region}"
  shared_credentials_file="${pathexpand("~/.aws/credentials")}"
}

resource "aws_key_pair" "keypairdetails" {
	key_name = "${var.ecs_key_name}"
	public_key = "${file("techradarkey.pem")}"
}
  
 resource "aws_instance" "instance_details"{
   ami	= "ami-b374d5a5"
   instance_type="t2.micro"
	security_groups = ["${aws_security_group.security_group.id}"]
	key_name="{aws_key_pair.keypairdetails.key_name}"
	lifecycle{
		create_before_destroy=true
	}
 }
   
data "aws_availability_zones" "all" {}


resource "aws_autoscaling_group" "auto_scaling_group"{
	launch_configuration="${aws_instance.instance_details.id}"
	min_size=1
	max_size=4
	enabled_metrics=["GroupMinSize", "GroupMaxSize", "GroupDesiredCapacity", "GroupInServiceInstances", "GroupTotalInstances"]
	metrics_granularity="1Minute"
	load_balancers=["${aws_elb.load_balancer.id}"]
	health_check_type="ELB"
	tag{
		key="Name"
		value="instance_details"
		propagate_at_launch=true
	}
}
	
resource "aws_autoscaling_policy" "scaling_policy"{
	name="techradar-autopolicy"
	scaling_adjustment=1
	adjustment_type="ChangeinCapacity"
	cooldown=300
	autoscaling_group_name="${aws_autoscaling_group.auto_scaling_group.name}"
}

resource "aws_security_group" "security_group" {
	name = "${var.security_group_name}"
	ingress {
		from_port = 80
		to_port = 80
		protocol = "tcp"
		cidr_blocks = ["0.0.0.0/0"]
	}
	
	lifecycle {
		create_before_destroy = true
	}
}

resource "aws_security_group_rule" "ssh" {
	security_group_id = "${aws_security_group.security_group.id}"
	type = "ingress"
	from_port = 22
	to_port = 22
	protocol = "tcp"
	cidr_blocks = ["${var.security_group_cidr_block}"]
}

resource "aws_security_group" "elb_security_group" {
	name = "${var.elb_security_group_name}"
	ingress {
		from_port = 80
		to_port = 80
		protocol = "tcp"
		cidr_blocks = ["0.0.0.0/0"]
	}

	egress {
		from_port = 0
		to_port = 0
		protocol = "-1"
		cidr_blocks = ["0.0.0.0/0"]
	}

	lifecycle {
		create_before_destroy = true
	}
}

resource "aws_elb" "load_balancer" {
	name = "${var.aws_elb_name}"
	availability_zones = ["${data.aws_availability_zones.all.names}"]
	security_groups = ["${aws_security_group.elb_security_group.id}"]
	access_logs {
		bucket = "elb-log.acorrea.com"
		bucket_prefix = "elb"
		interval = 5
	}

	listener {
		instance_port = 80
		instance_protocol = "http"
		lb_port = 80
		lb_protocol = "http"
	}

	health_check {
		healthy_threshold = 2
		unhealthy_threshold = 2
		timeout = 3
		target = "HTTP:80/"
		interval = 30
	}

	cross_zone_load_balancing = true
	idle_timeout = 400
	connection_draining = true
	connection_draining_timeout = 400

	tags {
		Name = "${var.aws_elb_name}"
	}
}