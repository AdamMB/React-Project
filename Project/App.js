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
import { Provider } from 'react-redux';
import Store from './Store/configureStore';
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
        <Provider store={Store}>
            <NativeComponent/>
          <View style={{flex:1}}>
            <UIComponent/>
          </View>
        </Provider>

        )

  }
}


