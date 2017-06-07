#!/usr/bin/env bash
# install script will make run.sh an executable and configure 
# WiFi settings if provided. 

# make sure script is being run as root
if [ $(id -u) -ne 0 ]; then
  printf "This script must be run as root. \n"
  exit 1
fi

# get username and proviz directory from variable file
USERNAME=$(sed '1!d' variable.txt)
PROVIZ_DIR=$(sed '2!d' variable.txt)

echo $USERNAME
echo $PROVIZ_DIR

# remove variable file
#rm variable.txt

# make run.sh an executable
echo "making run script executable"
chmod 755 $PROVIZ_DIR/run.sh

# read from properties file function
PROPERTY_FILE=.config.properties

function getProperty {
    PROP_KEY=$1
    PROP_VALUE=`cat $PROPERTY_FILE | grep "$PROP_KEY" | cut -d'=' -f2`
    echo $PROP_VALUE
}

echo "Reading property from $PROPERTY_FILE"
SSID=$(getProperty "wifi-ssid") 
echo $SSID

# set bluetooth and wifi settings
if [ "$SSID" != "" ] ; then

    echo "found wifi settings"

    # Network encryption method.
    # * 'WPA' for WPA-PSK/WPA2-PSK (note: most Wi-Fi networks use WPA);
    # * 'WEP' for WEP;
    # * 'Open' for open network (aka. no password).
    # ENCRYPTION='WPA'
    ENCRYPTION=$(getProperty "wifi-encryption")
    echo $ENCRYPTION

    # Network password. (WPA-PSK/WPA2-PSK password, or WEP key)
    PASSWORD=$(getProperty "wifi-psk")
    echo $PASSWORD
    
    # build the supplicant file that holds our configuration
    rm /etc/wpa_supplicant/wpa_supplicant.conf
    touch /etc/wpa_supplicant/wpa_supplicant.conf
    echo "ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev" >> /etc/wpa_supplicant/wpa_supplicant.conf
    echo "update_config=1" >> /etc/wpa_supplicant/wpa_supplicant.conf
    echo "" >> /etc/wpa_supplicant/wpa_supplicant.conf
    echo "network={" >> /etc/wpa_supplicant/wpa_supplicant.conf
    echo "    ssid=\"$SSID\"" >> /etc/wpa_supplicant/wpa_supplicant.conf

    case $ENCRYPTION in
    'WPA')
        echo "WPA settings"

        echo "    psk=\"$PASSWORD\"" >> /etc/wpa_supplicant/wpa_supplicant.conf
        ;;
    'WEP')
        echo "WEP setting"

        echo "    key_mgmt=NONE" >> /etc/wpa_supplicant/wpa_supplicant.conf
        echo "    wep_tx_keyidx=0" >> /etc/wpa_supplicant/wpa_supplicant.conf #this forces it to use wep_key0
        echo "    wep_key0=\"$PASSWORD\"" >> /etc/wpa_supplicant/wpa_supplicant.conf
        ;;
    'Open')
        echo "    key_mgmt=NONE" >> /etc/wpa_supplicant/wpa_supplicant.conf
        ;;
    *)
        ;;
    esac

    # closing brace
    echo "}" >> /etc/wpa_supplicant/wpa_supplicant.conf

    systemctl daemon-reload
    service networking restart
    ifup wlan0

else
    echo "no wifi settings found"
fi


# install dependencies for bluetooth operations

# change bluetooth service into compat mode

# set bluetooth settings


# echo $PROVIZ_DIR
# start proviz
# bash $PROVIZ_DIR/run.sh

