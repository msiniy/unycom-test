'use strict';

var webpack = require('webpack');

/**
 * Env
 * Get npm lifecycle event to identify the environment
 */
var ENV = process.env.npm_lifecycle_event;
var isTest = ENV === 'test' || ENV === 'test-watch';
var isProd = ENV === 'build';
console.log("isProd = " + isProd);

module.exports = {
  entry: './ui-src/main.js',
  output: {
    filename: './js/bundle.js'
  },

  mode: isProd ? 'production' : 'development',

  devServer: {
    contentBase: './ui-src',
    port: 3000
  }
};