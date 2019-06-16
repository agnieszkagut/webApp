import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import Moment from 'react-moment';

class ConversationComponent extends Component{
    render(){
        let conversationElement
        if(this.props.user===this.props.creatorId){
            conversationElement=<p><i>{this.props.messageBody}</i></p>
        }
        else{
            conversationElement=<p>{this.props.messageBody}</p>
        }
        return(
            <div>
                <Moment format="YYYY-MM-DD HH:mm">
                    {this.props.createDate}
                </Moment>
                {conversationElement}
                </div>
        )
    }
}

export default translate('common')(ConversationComponent);