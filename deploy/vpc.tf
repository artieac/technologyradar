resource "aws_vpc" "application_vpc" {
  cidr_block = "${var.vpc_cidr_block}"
  tags {
    Name = "${var.vpc_name}"
	ApplicationGroup = "${var.ecs_common_tag}"
  }
}

resource "aws_internet_gateway" "application_igw" {
  vpc_id = "${aws_vpc.application_vpc.id}"
  tags {
    Name = "${var.igw_name}"
	ApplicationGroup = "${var.ecs_common_tag}"
  }
}


resource "aws_subnet" "public_subnet" {
  vpc_id 		= "${aws_vpc.application_vpc.id}"
  cidr_block    = "${var.public_subnet_cidr}"
  
  availability_zone = "${var.availability_zone}"
  tags {
    Name = "${var.public_subnet_name}"
	ApplicationGroup = "${var.ecs_common_tag}"
  }
}


resource "aws_subnet" "example" {

  for_each = var.availability_zones
	  vpc_id 		= "${aws_vpc.application_vpc.id}"
	  count 		= "${var.availability_zone_count}"
	  availability_zone = each.value
	  cidr_block        = cidrsubnet(aws_vpc.example.cidr_block, 8, count.index)
	  
	  tags {
		Name = "${var.public_subnet_name}"
		ApplicationGroup = "${var.ecs_common_tag}"
	  }
}


resource "aws_route_table" "application_route_table" {
  vpc_id = "${aws_vpc.application_vpc.id}"
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.application_igw.id}"
  }
  tags {
    Name = "${var.route_table_name}"
	ApplicationGroup = "${var.ecs_common_tag}"
  }
}

resource "aws_route_table_association" "application_subnet_association" {
  subnet_id = "${aws_subnet.public_subnet.id}"
  route_table_id = "${aws_route_table.application_route_table.id}"
}