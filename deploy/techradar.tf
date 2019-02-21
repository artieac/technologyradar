
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
 
   
data "aws_availability_zones" "all" {}

resource "aws_ecr_repository" "container_repository" {
  name = "${var.container_repository_name}"
} 

