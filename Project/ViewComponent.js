import React, { Component } from 'react'
import { requireNativeComponent, ViewPropTypes } from 'react-native'
import PropTypes from 'prop-types';
var viewProps = {
    name: 'ViewModule',
    propTypes:{
        text: PropTypes.string,
        ...ViewPropTypes,
    }
}

module.exports = requireNativeComponent('ViewModule', viewProps);

