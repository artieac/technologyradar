resource "aws_launch_configuration" "ecs-launch-configuration" {
    name  = "${var.launch_configuration_name}"
	image_id = "${lookup(var.amis, var.aws_region)}"
	instance_type="${var.instance_type}"
    security_groups = ["${aws_security_group.public_security_group.id}"]
	key_name="{aws_key_pair.keypairdetails.key_name}"
    
    lifecycle {
      create_before_destroy = true
    }
}

resource "aws_ecs_cluster" "container-services-cluster" {
    name = "${var.ecs_cluster}"
}

data "aws_ecs_task_definition" "ecs_task_definition" {
  task_definition = "${aws_ecs_task_definition.ecs_task_definition.family}"
}

resource "aws_ecs_task_definition" "ecs_task_definition" {
    family                = "${var.task_definition_family}"
	requires_compatibilities = ["FARGATE"]
	network_mode = "awsvpc"
    container_definitions = <<EOF
	[
	  {
		"name": "alwaysmoveforward/technologyradar",
		"image": "alwaysmoveforward/technologyradar",
		"essential": true,
		"portMappings": [
		  {
			"containerPort": 80,
			"hostPort": 80
		  }
		],
		"memory": 500,
		"cpu": 10
	  }]
	EOF
}
 

resource "aws_ecs_service" "ecs-service" {
  	name            = "${var.service_definition_name}"
  	iam_role        = "${var.ecs_service_role}"
  	cluster         = "${aws_ecs_cluster.container-services-cluster.id}"
  	task_definition = "${aws_ecs_task_definition.ecs_task_definition.family}:${max("${aws_ecs_task_definition.ecs_task_definition.revision}", "${data.aws_ecs_task_definition.ecs_task_definition.revision}")}"
  	desired_count   = 2
	launch_type     = "FARGATE"
	
  	load_balancer {
    	target_group_arn  = "${aws_alb_target_group.target_group.arn}"
    	container_port    = 80
    	container_name    = "technologyradar"
	}
}