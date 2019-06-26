import React, { Component } from "react";
import ConversationComponent from "./ConversationComponent";
import { translate, Trans } from 'react-i18next';

class Conversation extends Component{
    constructor(props) {
        super(props)
        this.state = {conversation: []}
    }
    componentDidMount(){
        const URL = "http://localhost:8080"
        fetch(URL + "/messages/" +  this.props.messageId + "/replies",{
            headers:{
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        })
            .then(res => res.json())
            .then(data => {
                this.setState({conversation: data})
            })
    }
    render(){
        const that = this.props.user
        return(
            <ul>
                {this.state.conversation.map(function(conversation,index) {
                        return (
                            <div key={index}>
                                <ConversationComponent messageBody={conversation.messageBody}
                                                       creatorId={conversation.creatorId}
                                                       user={that}
                                                       createDate={conversation.createDate}
                                                       key={index} />
                            </div>
                        )
                    }
                )}
            </ul>
        )
    }
}

export default translate('common')(Conversation);