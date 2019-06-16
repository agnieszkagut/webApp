import React, { Component } from "react";
import "./App.css";
import { translate, Trans } from 'react-i18next';


class NewUser extends Component {
    constructor(props){
        super(props)
        this.state={
            doubledPassword: "",
            ok: false,
            errorStatus: false,
            errorPassword : false,
            errorNull : false,
            userEntity:{
                email: "",
                username: "",
                realname: "",
                password: "",
                position: ""
            }
        }
        this.clear = this.clear.bind(this);
        this.handleChangeOnDoubledPassword = this.handleChangeOnDoubledPassword.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangeRealname = this.handleChangeRealname.bind(this);
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePosiotion = this.handleChangePosiotion.bind(this);
    }
    handleChangeOnDoubledPassword(event){
        this.setState({doubledPassword: event.target.value})
    }
    handleChangePassword(event) {
        const userEntity = this.state.userEntity
        userEntity.password = event.target.value
        this.setState({ userEntity: userEntity })
    }
    handleChangeUsername( event) {
        const userEntity = this.state.userEntity
        userEntity.username = event.target.value
        this.setState({ userEntity: userEntity })
    }
    handleChangeRealname(event) {
        const userEntity = this.state.userEntity
        userEntity.realname = event.target.value
        this.setState({ userEntity: userEntity })
    }
    handleChangeEmail(event) {
        const userEntity = this.state.userEntity
        userEntity.email = event.target.value
        this.setState({ userEntity: userEntity })
    }
    handleChangePosiotion(event) {
        const userEntity = this.state.userEntity
        userEntity.position = event.target.value
        this.setState({ userEntity: userEntity })
    }
    clear(){
        let x="clear"
        console.log(x)
        this.setState({doubledPassword: ""})
        const userEntity = this.state.userEntity
        userEntity.email = ""
        userEntity.username = ""
        userEntity.realname = ""
        userEntity.password = ""
        userEntity.position = ""
        this.setState({ userEntity: userEntity })
    }
    render() {
        return (
            <div>
                {this.state.ok
                    ?<div className="alert alert-dismissible alert-success">
                        <button type="button" className="close" data-dismiss="alert" onClick={()=>{
                            this.setState({ok: false})}}>&times;</button>
                        <strong>{this.props.t('warnings.ok', {framework: "react-i18next"})}</strong>
                    </div>
                    :this.state.errorPassword
                        ?<div className="alert alert-dismissible alert-danger">
                        <button type="button" className="close" data-dismiss="alert" onClick={()=>{
                            this.setState({errorPassword: false})
                        }}>&times;</button>
                        <h4>!!!!</h4>
                        <p>{this.props.t('warnings.passwords', {framework: "react-i18next"})}</p>
                        </div>
                        :this.state.errorNull
                                    ?<div className="alert alert-dismissible alert-danger">
                                        <button type="button" className="close" data-dismiss="alert" onClick={()=>{
                                            this.setState({errorNull: false})
                                        }}>&times;</button>
                                        <h4>!!!!</h4>
                                        <p>{this.props.t('warnings.null', {framework: "react-i18next"})}</p>
                                    </div>
                                    :this.state.errorStatus
                                            ?<div className="alert alert-dismissible alert-danger">
                                                <button type="button" className="close" data-dismiss="alert" onClick={()=>{
                                                    this.setState({errorStatus: false})
                                                }}>&times;</button>
                                                <h4>!!!</h4>
                                                <p>{this.props.t('warnings.invalid', {framework: "react-i18next"})}</p>
                                            </div>
                                :null}
                <div>
                    <form>
                        <fieldset style={{width:"70%", background:"lightgreen"}}>
                    <legend style={{background:"lightgreen"}}>{this.props.t('prompts.registration', {framework: "react-i18next"})}</legend>
                    <div className="col-lg-10">
                        <label htmlFor="inputEmail" className="control-label" >Email</label>
                        <div>
                            <input type="text" className="form-control" id="inputEmail" placeholder="Email"
                                   onChange={
                                       this.handleChangeEmail
                                   }/>
                        </div>
                        <div className="col-lg-10">
                            <label className="control-label" htmlFor="inputDefault">{this.props.t('prompts.username', {framework: "react-i18next"})}</label>
                            <input type="text" className="form-control" id="inputDefault1"
                                   onChange={
                                       this.handleChangeUsername
                                   }/>
                        </div>
                        <div className="col-lg-10">
                            <label className="control-label" htmlFor="inputDefault">{this.props.t('prompts.realname', {framework: "react-i18next"})}</label>
                            <input type="text" className="form-control" id="inputDefault2"
                                   onChange={
                                       this.handleChangeRealname
                                   }/>
                        </div>
                        <div className="col-lg-10">
                            <label htmlFor="inputPassword" className="col-lg-10 control-label">{this.props.t('prompts.password', {framework: "react-i18next"})}</label>

                            <input type="password" className="form-control" id="inputPassword1" placeholder={this.props.t('prompts.password', {framework: "react-i18next"})}
                                   onChange={
                                       this.handleChangePassword
                                   }/>
                        </div>
                        <div className="col-lg-10">
                            <label htmlFor="inputPassword" className="col-lg-10 control-label">{this.props.t('prompts.doublePassword', {framework: "react-i18next"})}</label>
                            <input type="password" className="form-control" id="inputPassword2" placeholder={this.props.t('prompts.doublePassword', {framework: "react-i18next"})}
                                   onChange={
                                       this.handleChangeOnDoubledPassword
                                   }/>
                        </div>
                        <div className="col-lg-10">
                            <label className="control-label" htmlFor="inputDefault">{this.props.t('prompts.position', {framework: "react-i18next"})}</label>
                            <input type="text" className="form-control" id="inputDefault3"
                                   onChange={
                                       this.handleChangePosiotion
                                   }/>
                        </div>
                    </div>
                        </fieldset>
                    </form>
                        <div style={{width:"70%", background:"lightgreen"}}>
                            <button className={this.props.buttonStyle} style={{border:"2px solid lightgreen"}} onClick={()=>{
                                this.clear()
                            }}>
                                {this.props.t('buttons.cancel', { framework: "react-i18next" })}
                            </button>
                            <button className={this.props.buttonStyle} style={{border:"2px solid lightgreen"}} onClick={()=>{
                                if (this.state.doubledPassword != this.state.userEntity.password) this.setState({errorPassword: true})
                                else {
                                    if (
                                        this.state.userEntity.email == "" ||
                                        this.state.userEntity.username == "" ||
                                        this.state.userEntity.realname == "" ||
                                        this.state.userEntity.password == "" ||
                                        this.state.userEntity.position == ""
                                    ) this.setState({errorNull: true})
                                    else {
                                        fetch('http://localhost:8080/login', {
                                            method: 'POST',
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type': 'application/json',
                                                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                                            },
                                            body: JSON.stringify(
                                                this.state.userEntity)
                                        })
                                            .then(res => res.text())
                                            .then(data => {
                                                if(data == "error") this.setState({errorStatus: true})
                                                if(data == "ok") this.setState({ok: true})
                                            })
                                    }
                                }
                                this.clear()
                            }}>
                                {this.props.t('buttons.submit', { framework: "react-i18next" })}
                            </button>
                        </div>
                </div>
            </div>
        )
    }
}


export default translate('common')(NewUser);


