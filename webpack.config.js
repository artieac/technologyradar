const path = require('path');
const webpack = require('webpack');

const ExtractTextPlugin = require("extract-text-webpack-plugin");

const extractLess = new ExtractTextPlugin({
	filename: "[name].[contenthash].css",
	disable: process.env.NODE_ENV === "development"
});

module.exports = {
	entry: {
		AdminApp: './src/main/resources/code/react/apps/AdminApp.js',
		MainApp: './src/main/resources/code/react/apps/MainApp.js'
	},
	output: {
		filename: '[name].js',
		path: path.resolve(__dirname, './src/main/resources/static/script/dist')
	},
	module: {
		loaders: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				loader: 'babel-loader',
				query: {
					presets: ['react', 'es2015']
				}
			},
			{
				test: /\.less/,
				loaders: ['style-loader', 'css-loader', 'less-loader']
			}]
	},
	resolve: {
		extensions: ['*', '.js', '.jsx', '.less']
	},
	plugins: [
		extractLess
	]
};