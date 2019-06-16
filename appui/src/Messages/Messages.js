import React, { Component } from "react";
import MessageComponent from "./MessageComponent";
import NewMessage from "./NewMessage";
import { translate, Trans } from 'react-i18next';
import {Row} from "reactstrap";

class Messages extends  Component{
    constructor(props) {
        super(props)
        this.state = {receivedMessages: [],
            sentMessages: [],
            showTheConversation: false,
            showTheMessage: false,
            displayMode: 0}
        this.handleData1 = this.handleData1.bind(this);
        this.handleData2 = this.handleData2.bind(this);
    }
    componentDidMount(){
        const URL = "http://localhost:8080"
        fetch(URL + "/messages/byRecipient/" +  this.props.user,
            {headers:{'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then(res => res.json())
            .then(data => {
                this.handleData1(data)
            })
        fetch(URL + "/messages/byCreator/" + this.props.user,
            {headers:{'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                }})
            .then(res => res.json())
            .then(data => {
                this.handleData2(data)
            })
    }
    handleData1(data): void{
        this.setState({receivedMessages: data})
    }
    handleData2(data): void{
        this.setState({sentMessages: data})
    }
    render(){
        const credentials = this.props.credentials
        const buttonStyle = this.props.buttonStyle
        let buttonReceived
        if(this.state.displayMode !== 0){
            buttonReceived=<button className={buttonStyle} onClick={()=> this.setState({displayMode: 0})}>
                    <span>{this.props.t('messages.received', { framework: "react-i18next" })}</span>
            </button>
        }
        else
            buttonReceived = <button className={this.props.buttonStyleDisabled}>{this.props.t('messages.received', { framework: "react-i18next" })}</button>

        let buttonSend
        if(this.state.displayMode !== 1){
            buttonSend=<button className={buttonStyle} onClick={()=> this.setState({displayMode: 1})}>
                    <span>{this.props.t('messages.sent', { framework: "react-i18next" })}</span>
            </button>
        }
        else
            buttonSend = <button className={this.props.buttonStyleDisabled}>{this.props.t('messages.sent', { framework: "react-i18next" })}</button>


        let buttonNew
        if(this.state.displayMode !== 2){
            buttonNew=<button className={buttonStyle} onClick={() => this.setState({displayMode: 2})}>
                    <span>{this.props.t('messages.new', { framework: "react-i18next" })}</span>
            </button>
        }
        else
            buttonNew = <button className={this.props.buttonStyleDisabled}>{this.props.t('messages.new', { framework: "react-i18next" })}</button>


        let displayedComponent
        const that = this.props.user
        if(this.state.displayMode === 0){
            displayedComponent=<div>
                <ul>
                    {this.state.receivedMessages.map(function(message,index) {
                            return (
                                <div key={index}>
                                    <MessageComponent
                                        messageId={message.messageId}
                                        messageBody={message.messageBody}
                                        subject={message.subject}
                                        creatorId={message.creatorId}
                                        isRead={message.isRead}
                                        createDate={message.createDate}
                                        user={that}
                                        key={index}
                                        buttonStyle={buttonStyle}
                                        credentials={credentials}
                                    />
                                </div>
                            )
                        }
                    )}
                </ul>
            </div>
        }
        if(this.state.displayMode === 1){
            displayedComponent=<div>
                <ul>
                    {this.state.sentMessages.map(function(message,index) {
                            return (
                                <div key={index}>
                                    <MessageComponent
                                        messageId={message.messageId}
                                        messageBody={message.messageBody}
                                        subject={message.subject}
                                        creatorId={message.creatorId}
                                        isRead={message.isRead}
                                        createDate={message.createDate}
                                        user={that}
                                        key={index}
                                        buttonStyle={buttonStyle}
                                        credentials={credentials}
                                    />
                                </div>
                            )
                        }
                    )}
                </ul>
            </div>
        }
        if(this.state.displayMode === 2){
            displayedComponent=<div>
                                    <NewMessage creatorId={this.props.user}
                                                buttonStyle={buttonStyle}
                                                credentials={credentials}
                                    />
                                </div>
        }
        return (
            <div>
                <div>
                    <Row>
                        {buttonReceived}{buttonSend}{buttonNew}
                    </Row>
                    {displayedComponent}
                </div>

            </div>
        )
    }
}


export default translate('common')(Messages);