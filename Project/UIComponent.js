import React, { Component } from 'react';
import {Button, NativeModules, Text, View} from 'react-native';

class UIComponent extends  React.Component {
    render() {
        return (
            <View style={{backgroundColor: 'yellow'}}>
                <Button title={'test'} />
            </View>
        );
    }
}

export default UIComponent
