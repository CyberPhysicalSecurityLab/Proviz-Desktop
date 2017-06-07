#!/usr/bin/env bash
# setup script will create the basic proviz file structure in home 
# directory along with some files. It will copy app, src, static, run.sh 
# and properties file to proviz directory. Create a desktop entry and move
# into autostart folder. 

USERNAME=$(whoami)
PROVIZ_DIR="/home/$USERNAME/.proviz"
CURRENT_DIR=$(pwd)
DESKTOP_NAME="proviz-alpha.desktop"

echo "$USERNAME, $CURRENT_DIR"

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

# add desktop entry to desktop and add to that other place
function desktop_entry {

    echo "editing desktop entry"

    echo "[Desktop Entry]
Name=Proviz-alpha
Comment=
Exec=/home/$USERNAME/.proviz/run.sh
Icon=/home/$USERNAME/.proviz/static/Letter-P-icon.png
Terminal=false
Type=Application
StartupNotify=true
Categories=Application;" > $PROVIZ_DIR/$DESKTOP_NAME

    # add desktop entry to desktop 
    cp $PROVIZ_DIR/$DESKTOP_NAME /home/$USERNAME/Desktop/
    # add desktop entry to autostart at boot
    cp $PROVIZ_DIR/$DESKTOP_NAME /home/$USERNAME/.config/autostart/
}

# create variables file to be used by install script
function create_variable_file {

    if [ ! -f "variable.txt" ]
    then
        echo "creating variable file"
        touch variable.txt
    else
        echo "altering variable file"
        rm variable.txt
        touch variable.txt
    fi

    # write username and proviz dir for install script
    echo $"$USERNAME
$PROVIZ_DIR" > variable.txt

}

create_file_struct
copy_content
desktop_entry
create_variable_file
