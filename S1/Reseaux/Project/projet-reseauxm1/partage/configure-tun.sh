#!/bin/bash
ip -6 addr add $2 dev $1
ip link set $1 up
