const path = require('path');
const webpack = require('webpack');

const ExtractTextPlugin = require("extract-text-webpack-plugin");

const extractLess = new ExtractTextPlugin({
	filename: "[name].[contenthash].css",
	disable: process.env.NODE_ENV === "development"
});

module.exports = {
	entry: {
		AdminApp: './code/react/apps/admin/AdminApp.js',
		MainApp: './code/react/apps/MainApp.js',
		ManageRadarsApp: './code/react/apps/ManageRadars/ManageRadarsApp.js'
	},
	output: {
		filename: '[name].js',
//		path: path.resolve(__dirname, './static/script/dist')
		path: path.resolve(__dirname, '../../../target/classes/static/script/dist')
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
			},
			{
              test: /\.css$/,
              loader: 'style-loader'
            },
            {
              test: /\.css$/,
              loader: 'css-loader',
              query: {
                modules: true,
                localIdentName: '[name]__[local]___[hash:base64:5]'
              }
            }]
	},
	resolve: {
		extensions: ['*', '.js', '.jsx', '.less']
	},
	plugins: [
		extractLess
	]
};