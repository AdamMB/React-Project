import React, { Component } from 'react';
import {Button, NativeModules, Text, View} from 'react-native';
import {connect } from 'react-redux'

class UIComponent extends  React.Component {
    render() {
        return (
            <View style={{backgroundColor: 'yellow'}}>
                <Button title={'test'} onPress={() => this._setStatus()}/>
            </View>
        );
    }

    _setStatus() {
        const action = { type:"GOOD", value: 'you are Good'}
        this.props.dispatch(action)
    }
}

const mapStatusToProps = (state) => {return state}
export default connect(mapStatusToProps)(UIComponent)
