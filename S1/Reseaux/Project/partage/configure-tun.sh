#!/bin/bash
ip -6 addr add fc00:1234:ffff::1/64 dev tun0
ip link set tun0 up
