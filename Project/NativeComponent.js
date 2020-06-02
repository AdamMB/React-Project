import React, { Component } from 'react';
import {Button, NativeModules, Text, View} from 'react-native';

class NativeComponent extends  React.Component {
    render() {
        NativeModules.ActivityStart.displayActivity();
        return null;
    }
}

export default NativeComponent
