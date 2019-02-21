resource "aws_autoscaling_group" "ecs-autoscaling-group" {
    name                        = "${var.autoscaling_group_name}"
    max_size                    = "${var.max_instance_size}"
    min_size                    = "${var.min_instance_size}"
    desired_capacity            = "${var.desired_capacity}"
    vpc_zone_identifier         = ["${aws_subnet.public_subnet.id}"]
    launch_configuration        = "${aws_launch_configuration.ecs-launch-configuration.name}"
    health_check_type           = "ELB"
  }
	
resource "aws_autoscaling_policy" "scaling_policy"{
	name="techradar-autopolicy"
	scaling_adjustment=1
	adjustment_type="ChangeinCapacity"
	cooldown=300
	autoscaling_group_name="${aws_autoscaling_group.ecs-autoscaling-group.name}"
}
