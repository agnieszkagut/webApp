import {Col} from "reactstrap";
import React, { Component} from "react";
import Conversation from "./Conversation";
import {ButtonToolbar} from "react-bootstrap";
import Reply from "./Reply";
import { translate, Trans } from 'react-i18next';
import Moment from 'react-moment';

class MessageComponent extends Component{
    constructor(props) {
        super(props)
        this.state = {showTheConversation: false,
            showTheMessage: false,
            showReplyForm: 0,
            doSubmit: false}
        this.messageFunction = this.messageFunction.bind(this);
        this.conversationFunction = this.conversationFunction.bind(this);
        this.replyFunction = this.replyFunction.bind(this);
    }
    componentDidUpdate(prevProps: Readonly<P>, prevState: Readonly<S>, snapshot: SS): void {
        if(this.state.showReplyForm===2){
            this.setState({doSubmit: true})
            this.setState({showReplyForm: 0})
        }
    }

    myCallback = () => {
        this.setState({ doSubmit: false })
    }
    messageFunction(){
        return <div>
                    <div>
                        <u>{this.props.t('messages.message', { framework: "react-i18next" })}</u>
                    </div>
                    <div>
                        <Moment format="YYYY-MM-DD HH:mm">
                            {this.props.createDate}
                        </Moment>
                        <p>{this.props.messageBody}</p>
                    </div>
        </div>
    }
    deleteConversation(){
        const URL = "http://localhost:8080"
        fetch(URL + "/messages/" + this.props.messageId, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        })
    }
    conversationFunction(){
        return  <div>
                    <div>
                        <u>{this.props.t('messages.conversation', { framework: "react-i18next" })}</u>
                    </div>
                    <div>
                        <Conversation
                            messageId={this.props.messageId}
                            user={this.props.user}
                            credentials={this.props.credentials}
                        />
                    </div>
                    <div>
                        <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=>this.deleteConversation()
                        }>
                            {this.props.t('messages.deleteConversation', { framework: "react-i18next" })}
                        </button>
                    </div>
        </div>
    }
    replyFunction(){
        return  <div>
                    <div>
                        <u>{this.props.t('messages.reply', { framework: "react-i18next" })}</u>
                    </div>
                    <div>
                        <Reply callbackFromParent={this.myCallback}
                               parentMessageId={this.props.messageId}
                               user={this.props.user}
                               creatorId={this.props.creatorId}
                               subject={this.props.subject}
                               doSubmit={this.state.doSubmit}
                               credentials={this.props.credentials}
                        />

                    </div>
        </div>
    }
    render(){
        let button
        if(this.state.showTheMessage||this.state.showTheConversation){
            button=<button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=>this.setState({showReplyForm: this.state.showReplyForm+1})}>
                {this.state.showReplyForm===0?
                    <text>{this.props.t('buttons.reply', { framework: "react-i18next" })}</text>
                    :<text>{this.props.t('buttons.send', { framework: "react-i18next" })}</text>
                }
            </button>
        }
        else button = null
        return(
            <div>
                <div>
                    {this.props.isRead?<p><u>{this.props.t('messages.subject', { framework: "react-i18next" })}</u><b>{this.props.subject}</b></p>
                    :<p><u>{this.props.t('messages.subject', { framework: "react-i18next" })}</u>{this.props.subject}</p>}
                </div>
                <div>
                    {this.state.showTheMessage?this.messageFunction()
                        :null}
                </div>
                <div>
                    {this.state.showTheConversation?this.conversationFunction()
                        :null}
                </div>
                <div>
                    {this.state.showReplyForm||this.state.doSubmit?this.replyFunction()
                        :null}
                </div>
                <div className="text-right">
                    <ButtonToolbar>
                        <Col>
                            {button}
                            <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=> this.setState({showTheMessage:!this.state.showTheMessage})}>
                                {this.state.showTheMessage?
                                    <span>{this.props.t('buttons.message.hide', { framework: "react-i18next" })}</span>
                                    :<span>{this.props.t('buttons.message.show', { framework: "react-i18next" })}</span>
                                }
                            </button>
                        </Col>
                        <Col>
                            <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=> this.setState({showTheConversation:!this.state.showTheConversation})}>
                                {this.state.showTheConversation?
                                    <span>{this.props.t('buttons.conversation.hide', { framework: "react-i18next" })}</span>
                                    :<span>{this.props.t('buttons.conversation.show', { framework: "react-i18next" })}</span>
                                }
                            </button>
                        </Col>
                    </ButtonToolbar>
                </div>
            </div>

        )
    }
}

export default translate('common')(MessageComponent);