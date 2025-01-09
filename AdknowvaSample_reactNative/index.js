/**
 * @format
 */
import React from 'react';
import { AppRegistry, Text, View, Button, NativeModules } from 'react-native';
import { name as appName } from './app.json';

const { BrowserModule } = NativeModules;

const App = () => {
 const openSapActivity = () => {
   const url = 'https://www.huvle.com'; // URL
   BrowserModule.openSapMainActivity(url); // native module call
 };
  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}>
      <Text style={{ fontSize: 24 }}>Hello React Native Test</Text>
      <View style={{ marginBottom: 10 }}>
        <Button title="Notification On" onPress={() => BrowserModule.openNotificationSettings()} />
      </View>
      <View style={{ marginBottom: 10 }}>
        <Button title="Notification Off" onPress={() => BrowserModule.turnOffNotification()} />
      </View>
      <View>
        <Button title="Open Huvle Browser" onPress={() => BrowserModule.openSapMainActivity()} />
      </View>
    </View>
  );
};

AppRegistry.registerComponent(appName, () => App);