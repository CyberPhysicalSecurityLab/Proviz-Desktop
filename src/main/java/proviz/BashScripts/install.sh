#!/usr/bin/env bash

USERNAME=$(whoami)
PROVIZ_DIR="/Users/$USERNAME/.proviz"
CURRENT_DIR=$(pwd)

echo "$USERNAME, $CURRENT_DIR"

# check for proviz directory
if [ ! -d "$PROVIZ_DIR" ]
then
    echo "proviz dir does not exit"
    mkdir -p $PROVIZ_DIR/app
    mkdir $PROVIZ_DIR/logs
    mkdir $PROVIZ_DIR/src
    mkdir $PROVIZ_DIR/pipes
    mkdir $PROVIZ_DIR/static
else
    echo "proviz dir does exit"
fi

# create log file
if [ ! -f "$PROVIZ_DIR/logs/proviz.log" ]
then
    echo "creating proviz log"
    touch $PROVIZ_DIR/logs/proviz.log
else
    echo "log exits"
fi

# create sensor out pipe
if [ ! -p "$PROVIZ_DIR/pipes/sensor_out_pipe" ]
then
    echo "Creating sensor out pipe"
    mkfifo $PROVIZ_DIR/pipes/sensor_out_pipe
else
    echo "sensor out pipe exits"
fi

# create control pipe
if [ ! -p "$PROVIZ_DIR/pipes/control_pipe" ]
then
    echo "Creating control pipe"
    mkfifo $PROVIZ_DIR/pipes/control_pipe
else
    echo "control pipe exits"
fi
