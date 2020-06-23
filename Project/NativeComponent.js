import React, { Component } from 'react';
import {Button, NativeModules, Text, View} from 'react-native';
import {DeviceEventEmitter} from 'react-native';
import {connect } from 'react-redux'

const initialState= {status:'GOOD'}

class NativeComponent extends  React.Component {

    componentDidMount(){
        DeviceEventEmitter.addListener('StatusEvent', (event)=>{
            console.log("status: ", event.state);
            const action = { type:"GOOD", value: event.state};
            this.props.dispatch(action)
        });
    }
    render() {
        NativeModules.ActivityStart.displayActivity();
        NativeModules.ActivityStart.getState();

        return null;
    }
}
const mapStatusToProps = (state) => {return state}
export default connect(mapStatusToProps)(NativeComponent)
