#!/usr/bin/env bash
USERNAME=$(whoami)
CURRENT_DIR=$(pwd)

if [ "$(uname)" == "Darwin" ]; then
	echo "Darwin"
	PROVIZ_DIR="/Users/$USERNAME/.proviz"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	echo "Linux"
	PROVIZ_DIR="/home/$USERNAME/.proviz"
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ]; then
	echo "mingw32 not supported yet"
	exit 1
fi


echo "$USERNAME, $CURRENT_DIR"
DESKTOP_NAME="proviz-alpha.desktop"


# create file structure and create pipe, log and properties file
function create_file_struct {
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

    # create properties file
    if [ ! -f "$PROVIZ_DIR/.config.properties" ]
    then
        echo "creating properties file"
        touch $PROVIZ_DIR/.config.properties
    else
        echo "properties file exits"
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


    if [ ! -f "$PROVIZ_DIR/$DESKTOP_NAME" ]
    then
        echo "Creating desktop file"
        echo  "$PROVIZ_DIR/$DESKTOP_NAME"
        touch $PROVIZ_DIR/$DESKTOP_NAME
    else
        echo "desktop file exit"
    fi
}

# move content to installation directory
function copy_content {

    echo "moving content to proviz directory"

    # clear out older application
    rm $PROVIZ_DIR/app/* $PROVIZ_DIR/src/*

    cp app/* $PROVIZ_DIR/app/       # copy app content
    cp src/* $PROVIZ_DIR/src/       # copy src content
    cp static/* $PROVIZ_DIR/static/ # copy static content
    cp run.sh $PROVIZ_DIR/          # copy run bash script
    cp .config.properties $PROVIZ_DIR/ # copy properties file

    echo "finished moving content to proviz directory"
}

create_file_struct
copy_content

