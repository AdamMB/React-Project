/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  NativeModules,
    Button,
    Platform
} from 'react-native';

import ToastExemple from './ToastExemple';


import UIComponent from './UIComponent';
import NativeComponent from './NativeComponent';
//import ViewModule from './ViewComponent';


//
// const App: () => React$Node = () => {
//   NativeModules.ActivityStart.displayActivity();
//   ToastExemple.show('test', ToastExemple.SHORT);
//   return (<UIComponent/>);
// };
const message = Platform.select({
  android: 'application android',
  web: 'application web'
});
export default class App extends React.Component {

  render() {
    //NativeModules.ActivityStart.displayActivity();
    //NativeModules.ActivityStart.showState();
    //ToastExemple.show(this.props.message, ToastExemple.SHORT);
    return (
      <View style={{flex:1}}>
        <Text> {message} </Text>
        <View style={{flex: 1, backgroundColor:'green'}}>
        </View>
      </View>)
  }
}


