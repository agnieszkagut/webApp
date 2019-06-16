import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';


class Login extends Component {
    constructor() {
        super();
        this.state = {
            badCredentialsError: false,
            username: "",
            password: "",
            error: "",
            emptyFieldsError: false
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleUserName = this.handleUserName.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
    }
    handleUserName(event){
        this.setState({username: event.target.value})
    }
    handlePassword(event){
        this.setState({password: event.target.value})
    }
    handleCancel(){
        this.setState({username:""})
        this.setState({password:""})
        this.setState({emptyFieldsError: false})
        this.setState({badCredentialsError: false})
    }
    handleSubmit(){
        const URL = "http://localhost:8080"
        if(this.state.username == "" || this.state.password == "") this.setState({emptyFieldsError: true})
        else (
            fetch(URL + "/login/" + this.state.username,
                {method:'GET',
                    headers: {'Authorization': 'Basic '+ btoa(this.state.username + ':' + this.state.password)},
                    credentials: 'same-origin'
                })
                .then((response) => {
                    if(!response.ok) throw new Error(response.status);
                    else return response.json();
                })
                .then((data) => {
                    this.props.callbackFromParent(data, this.state.username, this.state.password)
                })
                .catch((error) => {
                    this.setState({badCredentialsError: true})
                }))
    }
    render() {
        return (
            <div>
                {this.state.emptyFieldsError
                    ?<div className="alert alert-dismissible alert-danger">
                        <button type="button" className="close" data-dismiss="alert" onClick={() =>
                            this.setState({emptyFieldsError: false})}>&times;</button>
                        <strong>{this.props.t('warnings.empty', {framework: "react-i18next"})}</strong> {this.props.t('warnings.credentials', {framework: "react-i18next"})}
                    </div>
                    :this.state.badCredentialsError
                        ?<div className="alert alert-dismissible alert-danger">
                            <button type="button" className="close" data-dismiss="alert" onClick={() =>
                                this.setState({badCredentialsError: false})}>&times;</button>
                            <strong>{this.props.t('warnings.wrong', {framework: "react-i18next"})}</strong> {this.props.t('warnings.credentials', {framework: "react-i18next"})}
                        </div>
                        :null}
                <form className="form-horizontal">
                    <fieldset style={{width:"50%"}}>
                        <legend className="text-center">{this.props.t('login', { framework: "react-i18next" })}</legend>
                        <div className="form-group">
                            <label htmlFor="control-label" className="col-lg-2 control-label">{this.props.t('prompts.username', {framework: "react-i18next"})}</label>
                            <div className="col-lg-10">
                                <input type="text" className="form-control" id="inputText" placeholder={this.props.t('prompts.username', {framework: "react-i18next"})} onChange={this.handleUserName}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="inputDefault" className="col-lg-2 control-label">Password</label>
                            <div className="col-lg-10">
                                <input type="password" className="form-control" id="inputPassword"
                                       placeholder={this.props.t('prompts.password', {framework: "react-i18next"})} onChange={this.handlePassword}/>
                            </div>
                        </div>
                        <div className="form-group text-right">
                            <div className="col-lg-10 col-lg-offset-2">
                                <button type="reset" className="btn btn-default" style={{border: "1px solid white"}} onClick={() => this.handleCancel()}>{this.props.t('buttons.cancel', {framework: "react-i18next"})}</button>
                                <button type="button" className="btn btn-primary" style={{border: "1px solid white"}} onClick={() => this.handleSubmit()}>{this.props.t('buttons.submit', {framework: "react-i18next"})}</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        )
    }
}


export default translate('common')(Login);