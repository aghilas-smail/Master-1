#!/bin/bash
echo "deleting 'Wired connection 1' if exists"
nmcli connection del "Wired connection 1" || true

echo "deleting 'Wired connection 2' if exists"
nmcli connection del "Wired connection 2" || true

echo "configure interfaces from ansible"
sudo ansible-playbook -c local -v /vagrant/config.yml
