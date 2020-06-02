import React, { Component } from 'react';
import {Button, NativeModules, Text, View} from 'react-native';

class UIComponent extends  React.Component {
    render() {
        return (
            <View >
                <Text ceci est un test/>
                <Button title={'test'} />
            </View>
        );
    }
}

export default UIComponent
