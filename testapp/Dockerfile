FROM node:argon

# Create app directory
RUN mkdir -p /usr/src/app
RUN npm install express body-parser

WORKDIR /usr/src/app

COPY . /usr/src/app
# Install app dependencies
npm install
# Bundle app source

EXPOSE 3000

CMD [ "node", "app.js" ]
