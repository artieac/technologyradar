resource "aws_security_group_rule" "ssh" {
	security_group_id = "${aws_security_group.public_security_group.id}"
	type = "ingress"
	from_port = 22
	to_port = 22
	protocol = "tcp"
	cidr_blocks = [
         "${var.public_security_group_cidr_01}",
         "${var.public_security_group_cidr_02}"]
}

resource "aws_security_group" "public_security_group" {
    name = "${var.public_security_group_name}"
    description = "Public access security group"
    vpc_id = "${aws_vpc.application_vpc.id}"

   ingress {
       from_port = 22
       to_port = 22
       protocol = "tcp"
       cidr_blocks = [
          "0.0.0.0/0"]
   }

   ingress {
      from_port = 80
      to_port = 80
      protocol = "tcp"
      cidr_blocks = [
          "0.0.0.0/0"]
   }

   ingress {
      from_port = 8080
      to_port = 8080
      protocol = "tcp"
      cidr_blocks = [
          "0.0.0.0/0"]
    }

   ingress {
      from_port = 0
      to_port = 0
      protocol = "tcp"
      cidr_blocks = [
         "${var.public_security_group_cidr_01}",
         "${var.public_security_group_cidr_02}"]
    }

    egress {
        # allow all traffic to private SN
        from_port = "0"
        to_port = "0"
        protocol = "-1"
        cidr_blocks = [
            "0.0.0.0/0"]
    }

    tags { 
       Name = "${var.public_security_group_name}"
	   Name = "${var.ecs_common_tag}"
     }
}