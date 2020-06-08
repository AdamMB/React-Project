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
} from 'react-native';

import ToastExemple from './ToastExemple';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import UIComponent from './UIComponent'
import NativeComponent from './NativeComponent';
import ViewModule from './ViewComponent';
//
// const App: () => React$Node = () => {
//   NativeModules.ActivityStart.displayActivity();
//   ToastExemple.show('test', ToastExemple.SHORT);
//   return (<UIComponent/>);
// };

export default class App extends React.Component {

  render() {
    //NativeModules.ActivityStart.displayActivity();
    //NativeModules.ActivityStart.showState();
    //ToastExemple.show(this.props.message, ToastExemple.SHORT);
    return (
      <View style={{flex:1}}>
        <NativeComponent/>
        <View style={{flex: 1, backgroundColor:'green'}}>
          <UIComponent/>
        </View>
      </View>)
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});
