'use strict';

var webpack = require('webpack');
var CopyWebpackPlugin = require('copy-webpack-plugin')

/**
 * Env
 * Get npm lifecycle event to identify the environment
 */
var ENV = process.env.npm_lifecycle_event;
var isTest = ENV === 'test' || ENV === 'test-watch';
var isProd = ENV === 'build';

module.exports = {
  entry: './ui-src/main.js',
  output: {
    filename: './js/bundle.js'
  },

  mode: isProd ? 'production' : 'development',

  plugins: [
      new CopyWebpackPlugin([
        // copy index.html
        {
            from: 'ui-src/index.html',
            to: 'index.html'
        },
        // copy angular templates
        {
            from: 'ui-src/*/*.template.html',
            test: /(.+)\/(.+)\/(.+)\.html$/,
            to: '[2]/[name].[ext]'
        },
        // copy css
        {
            from: 'ui-src/css/*.css',
            to: 'css/[name].[ext]'
        },

      ])
  ],

  devServer: {
    contentBase: './ui-src',
    port: 3000,
    proxy: {
        '/api': 'http://localhost:8080'
    }
  }
};
