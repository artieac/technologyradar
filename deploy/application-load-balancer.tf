resource "aws_alb" "load-balancer" {
    name 				= "${var.elb_name}"
    security_groups 	= ["${aws_security_group.public_security_group.id}"]
    subnets             = ["${aws_subnet.public_subnet.id}"]

    tags {
		Name = "${var.elb_name}"
		Name = "${var.ecs_common_tag}"
	}
}

resource "aws_alb_target_group" "target_group" {
    name                = "${var.elb_tg_name}"
    port                = "80"
    protocol            = "HTTP"
    vpc_id              = "${aws_vpc.application_vpc.id}"

    health_check {
        healthy_threshold   = "5"
        unhealthy_threshold = "2"
        interval            = "30"
        matcher             = "200"
        path                = "/"
        port                = "traffic-port"
        protocol            = "HTTP"
        timeout             = "5"
    }

    tags {
		Name = "${var.aws_elb_tg_name}"
		Name = "${var.ecs_common_tag}"
	}
}

resource "aws_alb_listener" "alb-listener" {
    load_balancer_arn = "${aws_alb.load-balancer.arn}"
    port              = "80"
    protocol          = "HTTP"

    default_action {
        target_group_arn = "${aws_alb_target_group.target_group.arn}"
        type             = "forward"
    }
}